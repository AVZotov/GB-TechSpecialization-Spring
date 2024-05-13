import axios from "axios";

export const getAllCustomers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/customers`);
    } catch (error) {
        console.log(error);
    }
}

export const addNewCustomer = async (customer) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/customers/add`, customer);
    } catch (error){
        console.log(error);
    }
}