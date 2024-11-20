import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { HashRouter as Router, Routes, Route } from 'react-router-dom'
import './css/general.css'
import './css/shared.css'
import Home from './Home.jsx'
import Settings from './Settings.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Router>
      <Routes>
        <Route path='/' element={<Home/>}/>
        <Route path='/settings' element={<Settings/>}/>
      </Routes>
    </Router>
  </StrictMode>
)
