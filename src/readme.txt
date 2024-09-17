在JavaFX中关于拖放操作，可以设置这么几种事件监听器：

    setOnDragDetected(new EventHandler());
    当你从一个Node上进行拖动的时候，会检测到拖动操作，将会执行这个EventHandler。

    setOnDragEntered(new EventHandler());
    当你拖动到目标控件的时候，会执行这个事件回调。

    setOnDragExited(new EventHandler());
    当你拖动移出目标控件的时候，执行这个操作。

    setOnDragOver(new EventHandler());
    当你拖动到目标上方的时候，会不停的执行。

    setOnDragDropped(new EventHandler());
    当你拖动到目标并松开鼠标的时候，执行这个DragDropped事件。

    setOnDragDone(new EventHandler());
    当你拖动并松手的时候，执行Drag完成操作。