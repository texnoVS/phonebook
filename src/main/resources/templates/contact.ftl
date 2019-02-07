<#import "parts/common.ftl" as c>

<@c.page>

<form method="post" enctype="multipart/form-data">
    <#list viewContact as contact>
        <div class="photo-icon mb-3">
            <#if contact.filename??>
                <img src="/img/${contact.filename}">
            <#else>
                <img src="/img/Default.png">
            </#if>
        </div>
        <#if contact.filename??>
            <div class="row justify-content-md-center">
                <div class="col-md-auto">
                    <a href="/deleteIMG/${contact.id}" class="btn btn-outline-danger mb-4">Delete img</a>
                </div>
            </div>
            <#else >
        </#if>
        <div class="form-group row">
            <label for="staticEmail" class="col-sm-1 col-form-label">Surname</label>
            <div class="col-sm-11">
                <input class="form-control" type="text" name="surname"
                       value="${contact.surname}" placeholder="Enter surname" />
                <#if surnameError??>
                    <div class="invalid-feedback">
                        ${surnameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label for="staticEmail" class="col-sm-1 col-form-label">Name</label>
            <div class="col-sm-11">
                <input class="form-control" type="text" name="name" value="${contact.name}" placeholder="Enter name">
            </div>
        </div>
        <div class="form-group row">
            <label for="staticEmail" class="col-sm-2 col-form-label">Phone number</label>
            <div class="col-sm-10">
                <input id="phone" class="form-control" type="text" name="phone" value="${contact.phone}"/>
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
            <button id="btn-profile1" class="btn btn-primary" type="submit">Save</button>
            <a href="/delete/${contact.id}" id="btn-profile2" role="button" class="btn btn-danger">Delete contact</a>
        </div>
    </#list>
</form>

</@c.page>