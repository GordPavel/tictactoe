import {useState} from 'react'
import './MainPage.css'

import { Button, Input, Heading } from '@chakra-ui/react'

import { useToast } from '@chakra-ui/react'

import {fetchStartGame, getAllSession, getSession, startSession} from "../../api";
import {useNavigate} from "react-router-dom";

const MainPage = () => {
  const toast = useToast({
    isClosable: true,
    position: 'top',
  })

  const navigate = useNavigate();
  const [sessionId, setSessionId] = useState([])

  const [starting, setStarting] = useState(false)
  const [startingLoader, setStartingLoader] = useState(false)

  const [activeSession, setActiveSession] = useState({})

  const [botForm, setBotForm] = useState({
    bot1Id: 'test-bot-1',
    bot1Container: 'tic-tae-toe-bot:0.0.1',
    bot2Id: 'test-bot-2',
    bot2Container: 'tic-tae-toe-bot:0.0.1'
  })

  const checkValidateForm = () => {
    for (const key of Object.keys(botForm)) {
      if (botForm[key].length === 0) {
        return false;
      }
    }

    return true;
  }

  const handleStart = async () => {

    if (checkValidateForm()) {

      setStartingLoader(true);

      try {
        const sessionData = await startSession([botForm.bot1Id, botForm.bot2Id])

        console.log(sessionData)

        await fetchStartGame(botForm, sessionData.session_id);

        navigate(`/games/${sessionData.session_id}`);

      } catch (e) {
        toast({title: 'На сервере произошла ошибка', description: e.message, status: 'error'})
      } finally {
        setStartingLoader(false);
        setStarting(true);
      }

    } else {
      toast({description: 'Все поля формы должны быть заполнены', status: 'error', duration: 1000})
    }
  }
    return (
        <>
          <div className='start-form'>
            <div className='start-form__form'>
              <div className='start-form__col'>
                <Heading mb={4} as='h4' size='md' textAlign='center'>Бот 1</Heading>
                <Input value={botForm.bot1Id} mb={4} placeholder='ID' type="text"
                     onChange={(e) => setBotForm((prevState) => ({
                       ...prevState,
                       bot1Id: e.target.value
                     }))}/>
                <Input value={botForm.bot1Container} mb={4} placeholder='Image' type="text"
                       onChange={(e) => setBotForm((prevState) => ({
                         ...prevState,
                         bot1Container: e.target.value
                       }))}/>
              </div>
              <div className='vs-separator'>VS.</div>
              <div className='start-form__col'>
                <Heading mb={4} as='h4' size='md' textAlign='center'>Бот 2</Heading>
                <Input value={botForm.bot2Id} mb={4} placeholder='ID' type="text"
                     onChange={(e) => setBotForm((prevState) => ({
                       ...prevState,
                       bot2Id: e.target.value
                     }))}/>
                <Input value={botForm.bot2Container} mb={4} placeholder='Image' type="text"
                     onChange={(e) => setBotForm((prevState) => ({
                       ...prevState,
                       bot2Container: e.target.value
                     }))}/>
              </div>
            </div>

            <Button onClick={handleStart} size='lg' colorScheme='teal' isLoading={startingLoader}>Старт!</Button>
          </div>
        </>
    )
}


export default MainPage;
