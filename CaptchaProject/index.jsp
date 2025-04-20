<!DOCTYPE html>
<html>
<head>
    <title>Simple Form with CAPTCHA</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background: url('https://www.transparenttextures.com/patterns/cubes.png'), #0a0f2c; /* techy dark bg */
            /* background-size: cover; */
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: #ffffff;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            background: rgba(15, 23, 42, 0.95);
            padding: 40px 50px;
            border-radius: 20px;
            box-shadow: 0 0 25px rgba(0, 255, 255, 0.2);
            text-align: center;
            width: 100%;
            max-width: 450px;
        }

        .container h1 {
            margin-bottom: 25px;
            font-size: 28px;
            color: #00ffe7;
            border-bottom: 1px solid #00ffe7;
            padding-bottom: 10px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        input[type="text"],
        input[type="email"] {
            padding: 12px;
            border-radius: 8px;
            border: none;
            font-size: 16px;
            background-color: #1e293b;
            color: white;
            outline: none;
            transition: all 0.3s ease-in-out;
        }

        input[type="text"]:focus,
        input[type="email"]:focus {
            box-shadow: 0 0 8px #00ffe7;
        }

        input[type="submit"] {
            background-color: #00ffe7;
            color: #0f172a;
            padding: 12px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #00d6c9;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>My CAPTCHA Service</h1>
        <form action="CaptchaServlet" method="post">
            <input type="text" name="name" placeholder="Enter your name" required>
            <input type="email" name="email" placeholder="Enter your email" required>
            <input type="submit" value="Proceed to CAPTCHA">
        </form>
    </div>
</body>
</html>
