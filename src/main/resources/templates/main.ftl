<#import "parts/common.ftl" as c>

<@c.page>
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/main" class="form-inline">
            <input type="surname" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Search by surname">
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>
    </div>
</div>
<#--<div class="form-row">-->
    <#--<div class="form-group col-md-6">-->
        <#--<form method="get" action="/main" class="form-inline">-->
            <#--<input type="name" name="filterByName" class="form-control" value="${?ifExists}" placeholder="Search by name">-->
            <#--<button type="submit" class="btn btn-primary ml-2">Search</button>-->
        <#--</form>-->
    <#--</div>-->
<#--</div>-->

<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Add new contact
</a>
<div class="collapse" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input class="form-control" type="text" name="surname" placeholder="Введите Фамилию"/>
            </div>
            <div class="form-group">
                <input class="form-control" type="text" name="name" placeholder="Введите Имя">
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-group">
                <button class="btn btn-primary" type="submit">Добавить</button>
            </div>
        </form>
    </div>
</div>

<div class="card-columns mt-2">
    <#list contacts as contact>
        <a href="/profile/${contact.id}">
            <div class="card my-3">
                <div class="card-body">
                    <h5 class="card-text">
                        ${contact.surname}
                        ${contact.name}
                    </h5>
                </div>
            </div>
        </a>
        <#else>
        <h5>Nothing to show</h5>
    </#list>
</div>
</@c.page>