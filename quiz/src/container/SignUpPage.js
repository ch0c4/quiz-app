import React, { useContext, useState } from 'react';
import { Header, Grid, Form, Segment, Button, Message } from 'semantic-ui-react';
import { Link } from 'react-router-dom';
import { GlobalContext } from '../context/GlobalState';
import { signUp } from '../services/QuizApi';
import { signUpAction } from '../context/Action';

function SignUpPage(props) {
  const [email, setEmail] = useState('email@email.com');
  const [password, setPassword] = useState('azeqsd');
  const [hasError, setHasError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const { dispatch } = useContext(GlobalContext);

  async function handleSubmit() {
    try {
      const json = await signUp(email, password);
      dispatch(signUpAction({ id: json.id, email, connected: true }));
      props.history.push('/');
    } catch (error) {
      setHasError(true);
      setErrorMessage(error.message);
    }
  }

  return (
    <Grid textAlign="center" style={{ height: '100vh' }} verticalAlign="middle">
      <Grid.Column style={{ maxWidth: 450 }}>
        <Header as="h2" color="teal" textAlign="center">
          Sign up account
        </Header>
        <Form size="large" onSubmit={handleSubmit}>
          <Segment stacked>
            <Form.Input
              fluid
              icon="user"
              iconPosition="left"
              placeholder="E-mail address"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <Form.Input
              fluid
              icon="lock"
              iconPosition="left"
              placeholder="Password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />

            <Button color="teal" fluid size="large">
              Sign up.
            </Button>
          </Segment>
        </Form>
        <Message negative hidden={!hasError}>
          {errorMessage}
        </Message>
        <Message>
          Have an account? <Link to="/login">Login</Link>
        </Message>
      </Grid.Column>
    </Grid>
  );
}

export default SignUpPage;
