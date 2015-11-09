(function () {
    "use strict";

    window.addEventListener("load", main);

    function main() {

        var addressTable = document.getElementById("adresstable");
        console.log(addressTable);
        var req = new XMLHttpRequest();
        req.open("POST","/getEmails");

        req.onload = function () {

            //var em = "jake.dixon@my.jcu.edu.au,1" +"/n"+
            //    "jake_dixon@live.com.au,2";
            //console.log(em);
            //var emails = em.trim().split("/n");
            var emails = this.responseText.trim().split("\n");



            for (var emailIndex in emails){
                if (emails.hasOwnProperty(emailIndex)) {
                    var email = emails[emailIndex].trim().split(",");
                    emails[emailIndex] = email;

                }

            }
            console.log(email);
            console.log(emails);

            emails.forEach(function (email) {

                var row = document.createElement("tr");
                var address = document.createElement("td");
                address.innerText = email[0];
                row.appendChild(address);
                var subbut = document.createElement("td");
                var form = document.createElement("form");
                form.action = "/getEmails";
                form.method = "POST";
                var hid = document.createElement("input");
                hid.type = "hidden";
                hid.value = email[1];
                hid.name = "id";
                form.appendChild(hid);
                var inp = document.createElement("input");
                inp.type = "submit";
                inp.name = "button";
                inp.value = "delete";
                form.appendChild(inp);
                subbut.appendChild(form);
                row.appendChild(subbut);

                addressTable.appendChild(row);
            });


            var row = document.createElement("tr");
            //row.innerHTML = '<form action="/Email"><input type="email" name="address"/><input type="submit" formaction"/Email" value="add"></form>';
            var form = document.createElement("form");
            form.action = "/getEmails";
            form.method = "POST";
            var add = document.createElement("input");
            add.type = "email";
            add.name = "address";
            form.appendChild(add);
            var sub = document.createElement("input");
            sub.type = "submit";
            sub.name = "button";
            sub.value = "add";
            form.appendChild(sub);
            row.appendChild(form);

            addressTable.appendChild(row);

        };
        req.send();

    }
}());
/**
 * Created by magikarp on 21/05/2015.
 */
