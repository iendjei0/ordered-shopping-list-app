import './css/index.css'
import Header from './fragments/Header.jsx'
import Skeleton from './fragments/Skeleton.jsx'
import Footer from './fragments/Footer.jsx'

function Home() {
  return (
    <>
      <Header />
      <main>
        <div class="create-button">
          <button>Create</button>
        </div>
        <Skeleton />
      </main>
      <Footer text="Settings"/>
    </>
  )
}

export default Home
