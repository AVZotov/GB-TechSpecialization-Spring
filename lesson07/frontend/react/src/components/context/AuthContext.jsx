import {createContext, useContext, useState, useEffect} from "react";
import {login as performLogin} from "../../services/customers.js";
// import PropTypes from 'prop-types';

// AuthProvider.propTypes = {
//     children: PropTypes.node.isRequired,
// }



const AuthContext = createContext({});

const AuthProvider = ({children}) => {

    const [customer, setCustomer] = useState(null);

    const login = async (usernameAndPassword) => {
        return new Promise((resolve, reject) => {
            performLogin(usernameAndPassword).then(response => {
                const jwtToken = response.headers["authorization"]
                localStorage.setItem("access token", jwtToken)
                setCustomer({
                    ...response.data["customerDTO"]
                })
                resolve(response);
            }).catch(error => {
                reject(error)
            })
        })
    }

    return (
        <AuthContext.Provider
            value={{
                customer,
                login
            }}>
            {children}
        </AuthContext.Provider>
    )
}
export const useAuth = () => useContext(AuthContext);

export default AuthProvider;
