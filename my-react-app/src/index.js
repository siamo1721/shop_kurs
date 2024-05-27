import React from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';
import {store} from './redux/store';
import App from './components/App';
import './index.css';

const rootElement = document.getElementById('root');

// Создаем корневой элемент с помощью createRoot
const root = createRoot(rootElement);

// Рендерим приложение
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <App />
        </Provider>
    </React.StrictMode>
);
