import AdminPanel from './components/AdminPanel.jsx'
import HomePanel from './components/HomePanel.jsx'

// Cambiar a false para ver el HomePanel
const isLoggedIn = true

function App() {
  return (
    <main>
      <h1>Ejercicio 1 - Renderizado condicional</h1>

      {/* Si isLoggedIn es true se muestra AdminPanel, si no HomePanel */}
      {isLoggedIn ? <AdminPanel /> : <HomePanel />}
    </main>
  )
}

export default App
