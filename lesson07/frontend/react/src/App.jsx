import SidebarWithHeader from "./shared/SideBar.jsx";
import {useEffect} from "react";
import {getAllCustomers} from "./services/customers.js";
import DrawerForm from "./components/DrawerForm.jsx";

const App = () => {
    useEffect(() => {
        getAllCustomers().then((responce) => {
            console.log(responce);
        }).catch(error => {
            console.log(error);
        })
    }, [])
    return (
        <SidebarWithHeader>
            <DrawerForm/>
        </SidebarWithHeader>
    )

}

export default App
