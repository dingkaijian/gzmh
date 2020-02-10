// 单页需要引用

var BREAD_HEIGHT = 34

window.onload = function() {
    resizeFrame()
}

window.onresize = function() {
    resizeFrame()
}

/**
 * 调整主要内容尺寸,使得路径bread区域固定
 *
 */
function resizeFrame() {
    var viewHeight = document.documentElement.clientHeight
    var viewWidth = document.documentElement.clientWidth
    var contentHeight = viewHeight - BREAD_HEIGHT
    var contentWidth = viewWidth

    document.querySelector("#main-content").style.height =
        contentHeight - 40 + "px"
    document.querySelector("#main-content").style.width =
        contentWidth - 30 + "px"
    var tabContent = document.querySelector("#tab-content")
    if (tabContent) {
        tabContent.style.height = contentHeight - 110 + "px"
    }
}
