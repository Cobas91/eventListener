import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import App from './App'
import reportWebVitals from './reportWebVitals'
import { createTheme, ThemeProvider } from '@mui/material'
import { BrowserRouter as Router } from 'react-router-dom'
const themeOptions = {
  palette: {
    type: 'dark',
    primary: {
      main: '#0075a4',
    },
    secondary: {
      main: '#7ab030',
    },
    divider: '#F4F6F8',
    background: {
      default: '#F4F6F8',
      paper: '#cbcbcb',
    },
    text: {
      primary: '#000000',
      secondary: 'rgba(0,0,0,0.7)',
      disabled: 'rgba(72,71,71,0.5)',
      hint: 'rgba(57,57,57,0.5)',
    },
  },
  typography: {
    fontFamily: 'Inter',
  },
}
const theme = createTheme(themeOptions)

ReactDOM.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <Router>
        <App />
      </Router>
    </ThemeProvider>
  </React.StrictMode>,
  document.getElementById('root')
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
