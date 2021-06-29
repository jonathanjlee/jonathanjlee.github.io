$(document).ready(function() {
    // initMap();
    openSearchModal();
});

// function rotateImage(imgNumber) {
//     var images = ["images/homelander.jpg", "images/stormfront.jpg", "images/blacknoir.jpg", "lamplighter.png"];
//     var imgShown = document.getElementById("menuBar").style.backgroundImage;
//     var newImgNumber = Math.floor(Math.random() * images.length);
//     document.getElementById("menuBar").style.backgroundImage = 'url(' + images[newImgNumber] + ')';
// }

// window.onload = rotateImage;

//  Google map on index.html
// let map;

// function initMap() {
//     map = new google.maps.Map(document.getElementById("map"), {
//         center: { lat: -34.397, lng: 150.644 },
//         zoom: 8
//     });
// }

function openSearchModal() {
    $("#searchSightingsModal").on("show.bs.modal", function() {
        var date = $("#dateSearch").val();
        $.get("/modals/modalSearchSightingByDate?date=" + date, function(data) {
            $("#searchSightingsModal").find(".modal-content").html(data);
        });
    });
}