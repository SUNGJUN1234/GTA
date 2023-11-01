/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import { AppContextProvider } from './src/global/AppContext';

const AppWithProvider = () => (
    <AppContextProvider>
      <App />
    </AppContextProvider>
  );

AppRegistry.registerComponent(appName, () => AppWithProvider);
