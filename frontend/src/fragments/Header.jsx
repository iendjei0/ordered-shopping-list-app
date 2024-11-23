import sharedStyles from '../css/Shared.module.css'

function Header() {
    return (
      <header>
        <div className={sharedStyles.logo}>
          <h1>Ordered Shopping List</h1>
        </div>
        <div className={sharedStyles['user-account']}>
          <h3>Account</h3>
          <button><i class="fa-solid fa-user"></i></button>
        </div>
      </header>
    )
}

export default Header