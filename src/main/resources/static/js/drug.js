/**
 * Created by zw on 2017/4/13.
 */
var currentFrameHistoryUrl="";//返回到哪个url界面
var currentFrameId="";//当前所处的currentFrameId
//返回操作
function backOperate() {
    this.parent.removeHistoryUrl(currentFrameId,location.href);
    location.href=currentFrameHistoryUrl;
}