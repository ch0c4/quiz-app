import React, { useState, useContext } from 'react';
import { Grid, Header, Form, Segment, Message, Button } from 'semantic-ui-react';
import { Link } from 'react-router-dom';
import { GlobalContext } from '../context/GlobalState';
import { loginAction } from '../context/Action';
import { login } from '../services/QuizApi';

function LoginPage(props) {
  const [email, setEmail] = useState('email@email.com');
  const [password, setPassword] = useState('azeqsd');
  const [hasError, setHasError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const { dispatch } = useContext(GlobalContext);

  async function handleSubmit() {
    try {
      const json = await login(email, password);
      dispatch(loginAction({ id: json.id, email, connected: true }));
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
          Log-in to your account
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
              Login
            </Button>
          </Segment>
        </Form>
        <Message negative hidden={!hasError}>
          {errorMessage}
        </Message>
        <Message>
          New to us? <Link to="/signup">Sign Up</Link>
        </Message>
      </Grid.Column>
    </Grid>
  );
}

export default LoginPage;
