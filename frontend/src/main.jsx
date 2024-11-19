import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './css/general.css'
import './css/shared.css'
import Home from './Home.jsx'
import Settings from './Settings.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Home />
  </StrictMode>,
)
