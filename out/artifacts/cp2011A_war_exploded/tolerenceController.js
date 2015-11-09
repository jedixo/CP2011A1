(function () {
    "use strict";

    window.addEventListener("load", main);

    function main() {

        var thresholdTable = document.getElementById("thtb");
        console.log(thtb);
        var req = new XMLHttpRequest();
        req.open("POST","/Threshold");

        req.onload = function () {


            var thresholds = this.responseText.trim().split("\n");
            //var thresholds = "temperature,40\nhumidity,20".trim().split("\n");


            for (var thresholdIndex in thresholds){
                if (thresholds.hasOwnProperty(thresholdIndex)) {
                    var threshold = thresholds[thresholdIndex].trim().split(",");
                    thresholds[thresholdIndex] = threshold;

                }

            }

            console.log(thresholds);
            //thresholds   =   [String sensor , String threshold]

            thresholds.forEach(function (threshold) {

                var row = document.createElement("tr");
                var labelh = document.createElement("th");
                var label = document.createElement("label");
                label.for = threshold[0] + "threshold";
                label.innerText = threshold[0];
                labelh.appendChild(label);
                row.appendChild(labelh);
                var thinput = document.createElement("td");
                var input = document.createElement("input");
                input.type = "number";
                input.name = threshold[0];
                input.value = parseInt(threshold[1]);
                input.required;
                thinput.appendChild(input);
                row.appendChild(thinput);

                thresholdTable.appendChild(row);
            });
        };
        req.send();

    }
}());
/**
 * Created by magikarp on 21/05/2015.
 */
