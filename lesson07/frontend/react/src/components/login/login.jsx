import {
    Flex,
    Heading,
    Stack,
    Image, Box, FormLabel, Input, Alert, AlertIcon, Button,
} from '@chakra-ui/react';
import {Formik, Form, useField} from "formik";
import * as Yup from 'yup'
import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";

const MyTextInput = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status="error" mt={2} color="red.500">
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const LoginForm = () => {
    const {login} = useAuth();
    const navigate = useNavigate();

    return (
        <Formik
            validateOnMount={true}
            validationSchema={
                Yup.object({
                    username: Yup.string().email("Must be valid email").required("Email is required"),
                    password: Yup.string()
                        .max(20, "Password can't be longer then 20 characters")
                        .required("You must enter password to proceed")
                })}
            initialValues={{username: '', password: ''}}
            onSubmit={(values, {setSubmitting}) => {
                setSubmitting(true);
                login(values).then(response => {
                    navigate("/dashboard")
                    console.log("Successfully logged in")
                }).catch(error => {
                    console.log(error.response.message, error.code);
                }).finally(() => setSubmitting(false));
            }}>

            {({isValid, isSubmitting}) => {
                return <Form>
                    <Stack spacing={10}>
                        <MyTextInput
                            label={"Email"}
                            name={"username"}
                            type={"email"}
                            placeholder={"example@email.com"}/>

                        <MyTextInput
                            label={"Password"}
                            name={"password"}
                            type={"password"}
                            placeholder={"enter your password"}/>

                        <Button
                            type={"submit"}
                            disabled={!isValid || isSubmitting}
                        >
                            Login
                        </Button>
                    </Stack>
                </Form>
            }}

        </Formik>
    )
}

const Login = () => {

    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} align={'center'} justify={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Heading fontSize={'2xl'} mb={15}>Sign in to your account</Heading>
                    <LoginForm/>
                </Stack>
            </Flex>
            <Flex flex={1}>
                <Image
                    alt={'Login Image'}
                    objectFit='cover'
                    src={
                        'https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1352&q=80'
                    }
                />
            </Flex>
        </Stack>
    );
}

export default Login;