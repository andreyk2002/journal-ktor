
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="static/page.css"/>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <img src="/static/ktor.png">
    <h1>Testing Ktor for webistes </h1>
    <p><i>Made by Ktor, kotlinx.html & Freemarker!</i></p>
    <hr>
    <#list entries as item>
        <div>
            <h3>${item.headline}</h3>
            <p>${item.description}</p>
        </div>
    </#list>
    <hr>
    <div>
        <h3>Add a new entry!</h3>
        <form action="/submit" method="post">
            <input type="text" name="headline">
            <br>
            <textarea name="description"></textarea>
            <br>
            <input type="submit">
        </form>
    </div>
</body>
</html>