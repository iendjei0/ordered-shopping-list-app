import './css/b-side.css'
import Header from './fragments/Header.jsx'
import Skeleton from './fragments/Skeleton.jsx'
import Footer from './fragments/Footer.jsx'

function Settings() {
  return (
    <>
      <Header />
      <main>
        <Skeleton />
      </main>
      <Footer text="Home"/>
    </>
  )
}

export default Settings
