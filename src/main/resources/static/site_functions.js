//update cart when load page

$(document).ready(updateCartStatus());

function updateCartStatus() {
    getData("/cart/count", null, {"#fullPrice": "fullPrice", "#productCount": "productCount"});
    if ($('#productCount').text() === '0') {
        $('#shopping-item').hide();
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
    for (let key in updatedElements) {
        $(key).text(elementsData[updatedElements[key]])
    }
}

function convertDataToJSON(targetData) {
    let result = {};
    for (let key in targetData) {
        result[targetData[key]] = $(key).text();
    }
    return JSON.stringify(result);
}
