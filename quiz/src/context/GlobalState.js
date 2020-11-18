import React, { createContext } from 'react';
import { useReducerWithLogger } from './LoggerReducer';
import AppReducer from './AppReducer';

export const initialState = {
  customer: {
    connected: true,
    id: 1,
    email: 'email@email.com',
  },
  quizzes: [],
  currentQuiz: {
    quizId: 1,
    questionId: 1,
    question: 'Which character was played by Dustin Diamond in the sitcom &#039;Saved by the Bell&#039;?',
    answers: ['Screech', 'Zack', 'Mr. Belding', 'A.C. Slater'],
    time: Date.now(),
  },
  finishQuiz: {
    totalCorrectAnswer: 10,
    timeFinish: 1998,
    questions: [
      {
        question: 'Which character was played by Dustin Diamond in the sitcom &#039;Saved by the Bell&#039;?',
        correctAnswers: 'Screech',
        isCorrect: true,
      },
      {
        question: 'In Game of Thrones, what is Littlefinger&#039;s real name?',
        correctAnswers: 'Petyr Baelish',
        isCorrect: true,
      },
      {
        question: 'When did the TV show Rick and Morty first air on Adult Swim?',
        correctAnswers: '2013',
        isCorrect: true,
      },
      {
        question: 'How many seasons did the Sci-Fi television show &quot;Stargate Atlantis&quot; have?',
        correctAnswers: '5',
        isCorrect: true,
      },
      {
        question:
          'In the television show Breaking Bad, what is the s…ame of Walter and Jesse&#039;s notorious product?',
        correctAnswers: 'Blue Sky',
        isCorrect: true,
      },
      {
        question:
          'What Nickelodeon game show featured a house on the…contestants would ransack to find hidden objects?',
        correctAnswers: 'Finders Keepers',
        isCorrect: true,
      },
      {
        question: 'Who played the Waitress in the Spam sketch of &quot;Monty Python&#039;s Flying Circus&quot;?',
        correctAnswers: 'Terry Jones',
        isCorrect: true,
      },
      {
        question: 'How many seasons did the Sci-Fi television show &quot;Stargate Universe&quot; have?',
        correctAnswers: '2',
        isCorrect: true,
      },
      { question: 'How many seasons did &quot;Stargate SG-1&quot; have?', correctAnswers: '10', isCorrect: true },
      {
        question:
          'What was the name of the teenage witch played by M…t who lives with her witch aunts Hilda and Zelda?',
        correctAnswers: 'Sabrina',
        isCorrect: true,
      },
    ],
  },
};

export const GlobalContext = createContext(initialState);
export const GlobalProvider = ({ children }) => {
  const [state, dispatch] = useReducerWithLogger(AppReducer, initialState);

  return (
    <GlobalContext.Provider
      value={{
        customer: state.customer,
        quizzes: state.quizzes,
        currentQuiz: state.currentQuiz,
        finishQuiz: state.finishQuiz,
        dispatch,
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};
