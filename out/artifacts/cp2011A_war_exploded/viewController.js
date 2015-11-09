/**
 * Created by magikarp on 17/05/2015.
 */

(function () {
    "use strict";
    window.addEventListener("load", main)

    function main() {
        var rawDataTable = document.getElementById("viewTable");

        var tsum = document.getElementById("tsum");
        var tmean = document.getElementById("tmean");
        var tsd = document.getElementById("tsd");

        var hsum = document.getElementById("hsum");
        var hmean = document.getElementById("hmean");
        var hsd = document.getElementById("hsd");

        var lsum = document.getElementById("lsum");
        var lmean = document.getElementById("lmean");
        var lsd = document.getElementById("lsd");


        var interval = 1000;
        var tempState = localStorage.getItem('temperature');
        var humidState = localStorage.getItem('humidity');
        var lightState = localStorage.getItem('light');



        setInterval(function() {
            var req = new XMLHttpRequest();
            console.log("working");
            req.open("POST","/getData");

            req.onload = function () {
                var data = this.responseText.trim().split(",");
                // [tempdata , humiddata, lightdata, tsum, tmean, tsd, hsum, hmean, hsd, lsum, lmean, lsd]
                var row = document.createElement("tr");


                rawDataTable.deleteRow(1);

                if (tempState === "true") {
                    var tempdata = document.createElement("td");
                    tempdata.innerText = data[0];
                    row.appendChild(tempdata);
                } else {
                    var tempdata = document.createElement("td");
                    tempdata.innerText = "";
                    tempdata.style.background = "grey";
                    row.appendChild(tempdata);
                }
                if (humidState == "true") {
                    var humiddata = document.createElement("td");
                    humiddata.innerText = data[1];
                    row.appendChild(humiddata);
                } else {
                    var humiddata = document.createElement("td");
                    humiddata.innerText = "";
                    humiddata.style.background = "grey";
                    row.appendChild(humiddata);
                }
                if (lightState == "true") {
                    var lightdata = document.createElement("td");
                    lightdata.innerText = data[2];
                    row.appendChild(lightdata);
                } else {
                    var lightdata = document.createElement("td");
                    lightdata.innerText = "";
                    lightdata.style.background = "grey";
                    row.appendChild(lightdata);
                }
                rawDataTable.appendChild(row);

                tsum.innerText = data[3];
                tmean.innerText = data[4];
                tsd.innerText = data[5];
                hsum.innerText = data[6];
                hmean.innerText = data[7];
                hsd.innerText = data[8];
                lsum.innerText = data[9];
                lmean.innerText = data[10];
                lsd.innerText = data[11];

            };
            req.send();
        },interval);



    }

})();