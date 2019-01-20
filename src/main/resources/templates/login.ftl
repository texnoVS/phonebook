<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    ${message?ifExists}
<br>
    <@l.login "/login" false/>
</@c.page>

