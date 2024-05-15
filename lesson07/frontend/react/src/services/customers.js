import axios from "axios";

const getAuthConfig = () => {
    return (
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("access token")}`
            }
        }
    )
}

export const getAllCustomers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/customers`,
            getAuthConfig());
    } catch (error) {
        console.log(error);
    }
}

export const findCustomerById = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/customers/${id}`,
            getAuthConfig());
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

export const updateCustomer = async (customer) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/customers/update`, customer);
    } catch (error){
        console.log(error);
    }
}

export const deleteCustomer = async (customer) => {
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/customers/${id}`, customer);
    } catch (error){
        console.log(error);
    }
}

export const login = async (usernameAndPassword) => {
    return await axios.post(
        `${import.meta.env.VITE_API_BASE_URL}/customers/auth/login`, usernameAndPassword
    )
}