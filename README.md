# TransView
界面中包含header和content两大部分，当header滑动到完全不可见时，content显示范围为整个界面。
header和content中都可以是任意组件。
主要处理view的onInterceptTouchEvent和onTouch事件。
在用户手指触摸结束之后使用VelocityTracker监听手指滑动的速度，手指松开之后使用ValueAnimation属性动画的插值，进行后续的自然滑动。