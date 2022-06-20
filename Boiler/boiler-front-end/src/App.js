import './App.css';

import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Explore } from './pages/explore';
import { MyMethods } from './pages/myMethods';
import { Create } from './pages/create';
import { View } from './pages/view';
import { Login } from './pages/login';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/explore" element={<Explore />} />
        <Route path="/my_methods" element={<MyMethods />} />
        <Route path="/create" element={<Create />} />
        <Route path="/view" element={<View />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
