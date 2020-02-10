var BANNER_HEIGHT = 90
var NAV_HEIGHT = 45
var FOOTER_HEIGHT = 90

window.onload = function() {
    resizeFrame()
}

window.onresize = function() {
    resizeFrame()
}

/**
 * 调整iframe窗口尺寸
 *
 */
function resizeFrame() {
    var viewHeight = document.documentElement.clientHeight
    var viewWidth = document.documentElement.clientWidth
    var frameHeight = viewHeight - BANNER_HEIGHT - NAV_HEIGHT - FOOTER_HEIGHT
    var frameWidth = viewWidth

    document.querySelector("#channelFrame").style.height = frameHeight + "px"
    document.querySelector("#channelFrame").style.width = frameWidth + "px"
}
