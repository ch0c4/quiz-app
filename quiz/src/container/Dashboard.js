import React, { useContext, useEffect, Fragment } from 'react';
import { fetchQuizzesAction, startQuizAction } from '../context/Action';
import { GlobalContext } from '../context/GlobalState';
import { fetchQuizzesByCustomerId, startQuiz } from '../services/QuizApi';
import { Card, Button, Header } from 'semantic-ui-react';

function Dashboard(props) {
  const { customer, quizzes, dispatch } = useContext(GlobalContext);

  async function handleStartQuiz() {
    const currentQuiz = await startQuiz(customer.id, 'TELEVISION', 'EASY');
    currentQuiz.time = Date.now();
    dispatch(startQuizAction(currentQuiz));
    props.history.push('/quiz');
  }

  useEffect(() => {
    async function fetchQuizzes() {
      const data = await fetchQuizzesByCustomerId(customer.id);
      dispatch(fetchQuizzesAction(data.quizzes));
    }
    fetchQuizzes();
  }, [customer, dispatch]);

  return (
    <Fragment>
      <br />
      <Header as="h2" textAlign="center">
        <Header.Content>Quizzes</Header.Content>
      </Header>
      <br />
      <Card.Group centered itemsPerRow={6}>
        <Card raised>
          <Card.Content header="&nbsp;" />
          <Card.Content textAlign="center">
            <Button primary size="massive" onClick={handleStartQuiz}>
              Start
            </Button>
          </Card.Content>
          <Card.Content extra>&nbsp;</Card.Content>
        </Card>
        {quizzes.map((quiz) => (
          <Card key={quiz.id} raised>
            <Card.Content header={quiz.name} />
            <Card.Content>
              {quiz.completed && (
                <Fragment>
                  <p>Completed : true</p>
                  <p>In : {quiz.timeFinished / 1000} s</p>
                </Fragment>
              )}
            </Card.Content>
            <Card.Content extra>
              {quiz.completed && <Button>Replay</Button>}
              {!quiz.completed && <Button>Play</Button>}
            </Card.Content>
          </Card>
        ))}
      </Card.Group>
    </Fragment>
  );
}

export default Dashboard;
