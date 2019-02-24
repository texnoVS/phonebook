<#import "parts/common.ftl" as c>

<@c.page>
<h3>User editor</h3>

<form action="/user" method="post">
    <div class="form-group row">
        <div class="col-sm-10 mt-2">
            <input class="form-control" type="text" name="username" value="${user.username}">
        </div>
    </div>
    <#list roles as role>
        <div class="form-check">
            <#--Методом Freemarker проверяем установку поле role (return true of false)-->
            <label><input class="form-check-label" type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
        </div>
    </#list>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button class="btn btn-primary mt-2" type="submit">Save</button>
</form>
</@c.page>
