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
<div class="collapse <#if contact??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input class="form-control ${(surnameError??)?string('is-invalid', '')}" type="text"
                       value="<#if contact??>${contact.surname}</#if>" name="surname" placeholder="Surname"/>
                <#if surnameError??>
                    <div class="invalid-feedback">
                        ${surnameError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input class="form-control" type="text" value="<#if contact??>${contact.name}</#if>"
                       name="name" placeholder="Name">
                <#if nameError??>
                    <div class="invalid-feedback">
                        ${nameError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input id="online_phone" class="form-control" type="tel" maxlength="50"
                       autofocus="autofocus"
                       pattern="\+7\s?[\(]{0,1}9[0-9]{2}[\)]{0,1}\s?\d{3}[-]{0,1}\d{2}[-]{0,1}\d{2}"
                       name="phone" placeholder="+7(___)___-__-__">
                <#if phoneError??>
                    <div class="invalid-feedback">
                        ${phoneError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-group">
                <button class="btn btn-primary" type="submit">Add</button>
            </div>
        </form>
    </div>
</div>

<div class="card-columns mt-3">
    <#list contacts as contact>
        <a href="/contact/${contact.id}">
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
        <h5>No contacts</h5>
    </#list>
</div>

<script type="text/javascript">
    function setCursorPosition(pos, e) {
        e.focus();
        if (e.setSelectionRange) e.setSelectionRange(pos, pos);
        else if (e.createTextRange) {
            var range = e.createTextRange();
            range.collapse(true);
            range.moveEnd("character", pos);
            range.moveStart("character", pos);
            range.select()
        }
    }

    function mask(e) {
        //console.log('mask',e);
        var matrix = this.placeholder,// .defaultValue
                i = 0,
                def = matrix.replace(/\D/g, ""),
                val = this.value.replace(/\D/g, "");
        def.length >= val.length && (val = def);
        matrix = matrix.replace(/[_\d]/g, function(a) {
            return val.charAt(i++) || "_"
        });
        this.value = matrix;
        i = matrix.lastIndexOf(val.substr(-1));
        i < matrix.length && matrix != this.placeholder ? i++ : i = matrix.indexOf("_");
        setCursorPosition(i, this)
    }
    window.addEventListener("DOMContentLoaded", function() {
        var input = document.querySelector("#online_phone");
        input.addEventListener("input", mask, false);
        input.focus();
        setCursorPosition(3, input);
    });
</script>
</@c.page>