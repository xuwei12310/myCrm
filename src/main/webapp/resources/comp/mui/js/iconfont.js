(function(window){var svgSprite='<svg><symbol id="icon-g-tool-view" viewBox="0 0 1024 1024"><path d="M509.894 665.218c-86.081 0-156.118-68.75-156.118-153.257 0-84.506 70.031-153.257 156.118-153.257 86.069 0 156.087 68.75 156.087 153.257 0 84.506-70.019 153.257-156.087 153.257zM509.894 408.706c-58.512 0-106.119 46.318-106.119 103.256 0 56.932 47.6 103.256 106.119 103.256 58.493 0 106.087-46.318 106.087-103.256 0-56.937-47.594-103.256-106.087-103.256z"  ></path><path d="M518.437 791.963c-0.019 0-0.032 0-0.044 0-2.206 0-4.456-0.024-6.675-0.081-214.626-0.249-363.512-256.606-369.75-267.518-4.387-7.682-4.387-17.119 0-24.806 6.244-10.919 155.282-267.525 370.075-267.525s363.837 256.606 370.081 267.525c4.275 7.481 4.387 16.631 0.319 24.225-5.874 10.957-146.394 268.181-364.006 268.181zM193.018 511.944c31.606 49.468 157.982 229.938 319.026 229.938 0.213 0 0.426 0 0.643 0.007 1.906 0.050 3.832 0.075 5.719 0.075 163.275 0.024 283.275-180.488 312.9-229.619-31.2-48.919-157.813-230.306-319.263-230.306-161.375 0-287.475 180.406-319.026 229.906z"  ></path></symbol><symbol id="icon-xiazai" viewBox="0 0 1024 1024"><path d="M493.48608 663.32672c5.09952 5.12 11.83744 7.2704 18.49344 6.9632 6.656 0.34816 13.37344-1.8432 18.49344-6.9632l168.46848-169.5744a24.6784 24.6784 0 0 0 0-34.73408 24.33024 24.33024 0 0 0-34.54976 0l-128.04096 128.83968V229.376c0-13.57824-10.91584-24.576-24.39168-24.576s-24.41216 10.99776-24.41216 24.576v358.46144l-128.02048-128.83968a24.30976 24.30976 0 0 0-34.54976 0 24.6784 24.6784 0 0 0 0 34.73408l168.50944 169.59488zM805.00736 770.048H218.97216c-13.47584 0-24.41216 10.99776-24.41216 24.576s10.93632 24.576 24.41216 24.576h586.0352c13.47584 0 24.43264-10.99776 24.43264-24.576s-10.9568-24.576-24.43264-24.576z" fill="#050505" ></path></symbol></svg>';var script=function(){var scripts=document.getElementsByTagName("script");return scripts[scripts.length-1]}();var shouldInjectCss=script.getAttribute("data-injectcss");var ready=function(fn){if(document.addEventListener){if(~["complete","loaded","interactive"].indexOf(document.readyState)){setTimeout(fn,0)}else{var loadFn=function(){document.removeEventListener("DOMContentLoaded",loadFn,false);fn()};document.addEventListener("DOMContentLoaded",loadFn,false)}}else if(document.attachEvent){IEContentLoaded(window,fn)}function IEContentLoaded(w,fn){var d=w.document,done=false,init=function(){if(!done){done=true;fn()}};var polling=function(){try{d.documentElement.doScroll("left")}catch(e){setTimeout(polling,50);return}init()};polling();d.onreadystatechange=function(){if(d.readyState=="complete"){d.onreadystatechange=null;init()}}}};var before=function(el,target){target.parentNode.insertBefore(el,target)};var prepend=function(el,target){if(target.firstChild){before(el,target.firstChild)}else{target.appendChild(el)}};function appendSvg(){var div,svg;div=document.createElement("div");div.innerHTML=svgSprite;svgSprite=null;svg=div.getElementsByTagName("svg")[0];if(svg){svg.setAttribute("aria-hidden","true");svg.style.position="absolute";svg.style.width=0;svg.style.height=0;svg.style.overflow="hidden";prepend(svg,document.body)}}if(shouldInjectCss&&!window.__iconfont__svg__cssinject__){window.__iconfont__svg__cssinject__=true;try{document.write("<style>.svgfont {display: inline-block;width: 1em;height: 1em;fill: currentColor;vertical-align: -0.1em;font-size:16px;}</style>")}catch(e){console&&console.log(e)}}ready(appendSvg)})(window)