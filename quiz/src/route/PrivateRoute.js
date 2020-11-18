import React, { useContext } from 'react';
import { GlobalContext } from '../context/GlobalState';
import { Route, Redirect } from 'react-router-dom';

export function PrivateRoute({ children, ...rest }) {
  const { customer } = useContext(GlobalContext);
  return (
    <Route
      {...rest}
      render={({ location }) =>
        customer.connected ? children : <Redirect to={{ pathname: '/login', state: { from: location } }} />
      }
    />
  );
}
