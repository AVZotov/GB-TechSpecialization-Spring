import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {useEffect, useState} from "react";
import {getAllCustomers} from "./services/customers.js";
import DrawerForm from "./components/DrawerForm.jsx";
import {Spinner, Text, Wrap, WrapItem} from "@chakra-ui/react";
import CardWithImage from "./components/Card.jsx";

const App = () => {

    const [Customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        getAllCustomers().then((response) => {
            setCustomers(response.data);
        }).catch(error => {
            console.log(error);
        }).finally(() => setLoading(false));
    }, [])

    if (loading)
        return <SidebarWithHeader>
            <Spinner
                thickness='4px'
                speed='0.65s'
                emptyColor='gray.200'
                color='blue.500'
                size='xl'/>
        </SidebarWithHeader>

    if (Customers.length <= 0) {
        return (
            <SidebarWithHeader>
                <Text>No customers available</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
            <DrawerForm/>
            <Wrap justify={"center"} spacing={"30px"}>
                {Customers.map((customer, index) => (
                    <WrapItem key={index}>
                        <CardWithImage
                            name={customer.name}
                            id={customer.id}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>
    )
}

export default App
