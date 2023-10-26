package ru.tinkoff.tictactoe.session.impl;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.GameService;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.SessionService;
import ru.tinkoff.tictactoe.session.exception.CannotFinishRegistration;
import ru.tinkoff.tictactoe.session.exception.SessionIsAlreadyFullException;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.model.SessionStatus;
import ru.tinkoff.tictactoe.session.model.SessionWithLastTurn;
import ru.tinkoff.tictactoe.session.util.lock.LocalLock;
import ru.tinkoff.tictactoe.session.util.lock.LockException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final GameService gameService;
    private final LocalLock lock;

    @Transactional
    @Override
    public Session createSession() {
        return sessionRepository.createSession();
    }

    @Transactional
    @Override
    public Figure registerBotInSession(UUID sessionId, String url, String botId) {
        try {
            log.trace("Bot {} starts registration in session {}", botId, sessionId);
            return lock.lockRegistration(sessionId, Duration.ofSeconds(10), () -> {
                Session session = sessionRepository.findBySessionId(sessionId);
                if (session.status() != SessionStatus.NEW) {
                    throw new SessionIsAlreadyFullException();
                }
                if (session.attackingBotUrl() == null) {
                    sessionRepository.setAttackingBot(sessionId, url, botId);
                    log.debug("Bot {} registered in session {} as attacking bot", botId, sessionId);
                    return GameService.ATTACKING_BOT_FIGURE;
                }
                sessionRepository.setDefendingBot(sessionId, url, botId);
                gameService.startGame(session.id());
                log.debug("Bot {} registered in session {} as defending bot", botId, sessionId);
                return GameService.DEFENDING_BOT_FIGURE;
            });
        } catch (LockException e) {
            throw new CannotFinishRegistration(sessionId, e);
        }
    }

    @Override
    public SessionWithLastTurn getSession(UUID sessionId) {
        return sessionRepository.findFetchLastTurnBySessionId(sessionId);
    }

    @Override
    public List<Session> getSessionsByIsActive(Boolean isActive) {
        log.info("SessionServiceImpl: getSessionsByIsActive stared with isActive = {}", isActive);
        if (isActive == null) {
            return sessionRepository.findAllSessions();
        }
        return sessionRepository.findSessionsByIsActive(isActive);
    }
}