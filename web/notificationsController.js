(function () {
    "use strict";

    window.addEventListener("load", main);

    function main() {

        var notificationTable = document.getElementById("notificationtable");
        console.log(notificationtable);
        var req = new XMLHttpRequest();
        req.open("POST","/getNotifications");

        req.onload = function () {

            var notifications = this.responseText.trim().split("\n");



            for (var notificationIndex in notifications){
                if (notifications.hasOwnProperty(notificationIndex)) {
                    var notification = notifications[notificationIndex].trim().split(",");
                    notifications[notificationIndex] = notification;

                }

            }
            console.log(notification);
            console.log(notifications);

            notifications.forEach(function (notification) {

                var row = document.createElement("tr");
                var note = document.createElement("td");
                note.innerText = notification[0];
                row.appendChild(note);
                var subbut = document.createElement("td");
                var form = document.createElement("form");
                form.action = "/getNotifications";
                form.method = "POST";
                var hid = document.createElement("input");
                hid.type = "hidden";
                hid.value = notification[1];
                hid.name = "id";
                form.appendChild(hid);
                var inp = document.createElement("input");
                inp.type = "submit";
                inp.name = "button";
                inp.value = "delete";
                form.appendChild(inp);
                subbut.appendChild(form);
                row.appendChild(subbut);

                notificationTable.appendChild(row);
            });
        };
        req.send();

    }
}());
/**
 * Created by magikarp on 21/05/2015.
 */
