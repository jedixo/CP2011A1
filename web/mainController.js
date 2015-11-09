/**
 * Created by magikarp on 12/05/2015.
 */
(function () {
    "use strict";

    window.addEventListener("load", main);

    function main() {

        var sensorTable = document.getElementById("setupTable");
        var req = new XMLHttpRequest();
        req.open("POST","/getSensors");

        req.onload = function () {

            var sensors = this.responseText.trim().split("\n");
            console.log(this.responseText);

            for (var sensorindex in sensors){
                if (sensors.hasOwnProperty(sensorindex)) {
                    var sensor = sensors[sensorindex].trim().split(",");
                    sensors[sensorindex] = sensor;

                }

            }


            sensors.forEach(function (sensor) {
                if (sensor[1] === '1') {
                    var checked = "checked";
                    localStorage.setItem(sensor[0],true);
                } else {
                    var checked = "";
                    localStorage.setItem(sensor[0],false);
                }

                var row = document.createElement("tr");
                var heading = document.createElement("th");
                heading.innerHTML = "<label for='" + sensor[0] + "'>" + sensor[0] + ": </label>";
                row.appendChild(heading);
                var details = document.createElement("td");
                details.innerHTML = "<input type='checkbox' name='sensor' value='" + sensor[0] + "' " + checked + " onchange='this.form.submit()' />";
                row.appendChild(details);

                sensorTable.appendChild(row);
            });
        };
        req.send();

    }
}());
