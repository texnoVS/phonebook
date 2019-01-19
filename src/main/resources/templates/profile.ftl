<#import "parts/common.ftl" as c>

<@c.page>

<form method="post" enctype="multipart/form-data">
    <#list viewContact as contact>
        <div class="photo-icon mb-4">
            <#if contact.filename??>
                <img src="/img/${contact.filename}">
                <#--<div id="img_btn">-->
                    <#--<a href="/delete/${contact.filename}" name="delFilename" class="btn btn-outline-danger btn-sm">Delete img</a>-->
                <#--</div>-->
            <#else>
                <img src="/img/Default.png">
            </#if>
        </div>
        <div class="form-group row">
            <label for="staticEmail" class="col-sm-1 col-form-label">Surname</label>
            <div class="col-sm-11">
                <input class="form-control" type="text" name="surname" value="${contact.surname}" placeholder="Enter surname" />
            </div>
        </div>
        <div class="form-group row">
            <label for="staticEmail" class="col-sm-1 col-form-label">Name</label>
            <div class="col-sm-11">
                <input class="form-control" type="text" name="name" value="${contact.name}" placeholder="Enter name">
            </div>
        </div>
        <div class="form-group row">
            <label for="staticEmail" class="col-sm-2 col-form-label">Change photo</label>
            <div class="custom-file col-sm-10 row">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="form-group">
            <button id="btn-profile1" class="btn btn-outline-primary btn-sm" type="submit">Save</button>
            <a href="/delete/${contact.id}" id="btn-profile2" role="button" class="btn btn-outline-danger btn-sm">Delete contact</a>
        </div>
    </#list>
</form>

</@c.page>