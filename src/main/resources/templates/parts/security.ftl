<#--Определение переменных во Freemarker-->
<#assign
    <#--Spring security помещает контекст Freemarker в специальный объект, позволяющий оперировать контекстом Spring security-->
    <#--Если объект определён в контексте, значит мы можем работать с сессией пользователя-->
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        <#--Переменная будет содержать пользователя, который описан в базе данных-->
        <#--Так же есть возможность вызывать методы класса User-->
        authentication = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = authentication.getUsername()
        isAdmin = authentication.isAdmin()
    >
<#else>
    <#assign
        name = "unknown"
        isAdmin = false
    >
</#if>