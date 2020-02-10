var CHANNEL_HEIGHT = 34
var CHANNEL_WIDTH = 210

window.onload = function() {
    resizeFrame()
}

window.onresize = function() {
    resizeFrame()
}

/**
 * 调整左侧导航和iframe窗口尺寸
 *
 */
function resizeFrame() {
    var viewHeight = document.documentElement.clientHeight
    var viewWidth = document.documentElement.clientWidth
    var navHeight = viewHeight - CHANNEL_HEIGHT
    var frameHeight = viewHeight
    var frameWidth = viewWidth - CHANNEL_WIDTH

    document.querySelector("#left-nav").style.height = navHeight + "px"
    document.querySelector("#contentFrame").style.height =
        frameHeight - 10 + "px"
    document.querySelector("#contentFrame").style.width = frameWidth + "px"
}
