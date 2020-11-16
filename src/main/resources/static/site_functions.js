//update cart when load page

$(document).ready(updateCartStatus());

function updateCartStatus() {
    console.log("LOG!send json");
    getData("/cart/count", null, {"#fullPrice": "fullPrice", "#productCount": "productCount"});
    console.log("update status");
    if ($('#productCount').text() === '0') {
        $('#shopping-item').hide();
    } else {
        $('#shopping-item').show();
    }
}

function addToCart(id, count) {
    getData("/cart/add", JSON.stringify({"id": id, "count": count}), {
        "#fullPrice": "fullPrice",
        "#productCount": "productCount"
    });
}

function getData(targetUrl, sendingData, updatedElements) {
    $.getJSON(targetUrl, sendingData, (in_data) => updateElements(updatedElements, in_data));
}

function updateElements(updatedElements, elementsData) {
    console.log("LOG!update element")
    for (let key in updatedElements) {
        $(key).text(elementsData[updatedElements[key]])
    }
    console.log("LOG!update element finished")
}

function convertDataToJSON(targetData) {
    let result = {};
    for (let key in targetData) {
        result[targetData[key]] = $(key).text();
    }
    return JSON.stringify(result);
}
