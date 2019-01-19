<#import "parts/common.ftl" as c>

<@c.page>
<h5>List of users</h5>
<br>
<table class="table">
    <thead class="thead-light">
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Role</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        <#list users as user>
            <tr>
                <td scope="row"> ${user.username} </td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${user.id}">edit</a></td>
            </tr>
        </#list>
    </tbody>
</table>
</@c.page>