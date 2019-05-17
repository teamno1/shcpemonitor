/*if($("#footer").width()-6 == $t.width()-5){//不存在滚动条
	$tWidth = $t.width();
	//监听左侧箭头隐藏与显现
	$('#sidebar-collapsed', top.parent.document , parent.document).on("click",function(){
		if($('#mainContent', top.parent.document,parent.document).css("margin-left")=="6px"){
			//alert($("#footer").width());
			$tWidth = $("#footer").width();
		}else if($('#mainContent', top.parent.document,parent.document).css("margin-left")=="206px"){
			//($("#footer").width());
			$tWidth = $("#footer").width()-1;
		}
	})
}else*/ 
$(function(){
	$('#footer table').each(function() {
		if($(this).find('thead').length > 0 && $(this).find('th').length > 0) {
			// Clone <thead>
			var $w	   = $(window),
				$t	   = $(this),
				$tWidth= $t.width(),
				$thead = $t.find('thead').clone(),
				$col   = $t.find('thead, tbody').clone();
			if($("#footer").width()-6 == $t.width()-5){
				//alert(3)
			}else if($("#footer").width() > $t.width()){//存在竖滚动条
				//alert(1)
				$tWidth=$("#footer").width();
				//监听左侧箭头隐藏与显现
				/*$('#sidebar-collapsed', top.parent.document , parent.document).on("click",function(){
					if($('#mainContent', top.parent.document,parent.document).css("margin-left")=="6px"){
						//alert($("#footer").width());
						$tWidth = $("#footer").width();
					}else if($('#mainContent', top.parent.document,parent.document).css("margin-left")=="206px"){
						//($("#footer").width());
						$tWidth = $("#footer").width()-1;
					}
				})*/
				cloneTable();
			}else{//存在横竖滚动条
				//alert(2)
				$tWidth= $t.width();
				cloneTable();
			}
			function cloneTable(){
				// Add class, remove margins, reset width and wrap table
				$t
				.addClass('sticky-enabled')
				.css({
					margin: 0
				}).wrap('<div class="sticky-wrap" style="height:100%;"><div id="sticky-old" style="width: 100%; overflow: auto;overflow-x:hidden;"></div></div>');

				if($t.hasClass('overflow-y')) $t.removeClass('overflow-y').parent().addClass('overflow-y');

				// Create new sticky table head (basic)
				$t.parent().before('<div id="sticky-thead" style="border-bottom:1px solid #ddd;"><table class="table table-bordered sticky-thead" style="min-width:100%;background:linear-gradient(to bottom, #f8f8f8 0, #ececec 100%)"/></div>');
				
				// If <tbody> contains <th>, then we create sticky column and intersect (advanced)
				if($t.find('tbody th').length > 0) {
					$t.after('<table class="sticky-col" /><table class="sticky-intersect" />');
				}

				// Create shorthand for things
				var $stickyHead  = $t.parent('#sticky-old').siblings('#sticky-thead').children(".sticky-thead"),
					$stickyCol   = $t.siblings('.sticky-col'),
					$stickyInsct = $t.siblings('.sticky-intersect'),
					$stickyWrap  = $t.parent('.sticky-wrap').parent("#footer");

				$stickyHead.append($thead);
				
				$stickyCol
				.append($col)
					.find('thead th:gt(0)').remove()
					.end()
					.find('tbody td').remove();

				$stickyInsct.html('<thead><tr><th>'+$t.find('thead th:first-child').html()+'</th></tr></thead>');
				
				// Set widths
				var setWidths = function () {
					var explorer = window.navigator.userAgent ;
					if(navigator.userAgent.search(/Trident/i)>=0){
						$t
						.find('thead th').each(function (i) {
							$stickyHead.find('th').eq(i).width($(this).outerWidth());
						})
						.end()
						.find('tr').each(function (i) {
							$stickyCol.find('tr').eq(i).height($(this).innerHeight());
						});
						$t.parent().height($("#footer").height()-53);
		            }else if(explorer.indexOf("Firefox") >= 0){
		            	if($t.parent().parent().parent("#footer").hasClass("footer")==true){//含有class名为footer的表格
							$t
							.find('thead th').each(function (i) {
								$stickyHead.find('th').eq(i).width($(this).width());
							})
							.end()
							.find('tr').each(function (i) {
								$stickyCol.find('tr').eq(i).height($(this).innerHeight());
							});
						}else{
							$t
							.find('thead th').each(function (i) {
								$stickyHead.find('th').eq(i).width($(this).outerWidth());
							})
							.end()
							.find('tr').each(function (i) {
								$stickyCol.find('tr').eq(i).height($(this).innerHeight());
							});
						}
		            }else if(explorer.indexOf("Chrome") >= 0){
		            	$t
						.find('thead th').each(function (i) {
							$stickyHead.find('th').eq(i).width($(this).outerWidth());
						})
						.end()
						.find('tr').each(function (i) {
							$stickyCol.find('tr').eq(i).height($(this).innerHeight());
						});
		            	$t.parent().height($("#footer").height()-53);
		            }
					
						// Set width of sticky table head
						$stickyHead.css({"width":$tWidth,"max-width":$tWidth});
						$stickyHead.parent().width($tWidth);
						$t.parent().width($tWidth);
						$t.css("margin-top",-$t.find("thead").height());
						$t.width($tWidth);
						$stickyHead.parent().height($t.find("thead").height());
						$stickyHead.height($t.find("thead").height());
						if(explorer.indexOf("Firefox") >= 0){
			            	if($t.width() <= $("#footer").width()){
			            		$t.parent().height($("#footer").height()-35);
			            	}else{
			            		$t.parent().height($("#footer").height()-53);
			            	}
						}
						// Set width of sticky table col
						$stickyCol.find('th').add($stickyInsct.find('th')).width($t.find('thead th').width())
					},
					repositionStickyHead = function () {
						// Return value of calculated allowance
						var allowance = calcAllowance();
					
						// Check if wrapper parent is overflowing along the y-axis
						if($t.height() > $stickyWrap.height()) {
							// If it is overflowing (advanced layout)
							// Position sticky header based on wrapper scrollTop()
							if($stickyWrap.scrollTop() > 0) {
								// When top of wrapping parent is out of view
								var scrollTop=$stickyWrap.scrollTop();
								localStorage.setItem("scrollTop",scrollTop)
								$stickyHead.add($stickyInsct).css({
									opacity: 1,
									top: $stickyWrap.scrollTop()
								});
							} else {
								// When top of wrapping parent is in view
								$stickyHead.add($stickyInsct).css({
									opacity: 1,
									top: 0
								});
							}
						}else {
							// If it is not overflowing (basic layout)
							// Position sticky header based on viewport scrollTop
							if($w.scrollTop() > $t.offset().top && $w.scrollTop() < $t.offset().top + $t.outerHeight() - allowance) {
								// When top of viewport is in the table itself
								$stickyHead.add($stickyInsct).css({
									opacity: 1,
									top: $w.scrollTop() - $t.offset().top
								});
							} else {
								// When top of viewport is above or below table
								$stickyHead.add($stickyInsct).css({
									opacity: 0,
									top: 0
								});
							}
						}
					},
					repositionStickyCol = function () {
						if($stickyWrap.scrollLeft() > 0) {
							// When left of wrapping parent is out of view
							$stickyCol.add($stickyInsct).css({
								opacity: 1,
								left: $stickyWrap.scrollLeft()
							});
						} else {
							// When left of wrapping parent is in view
							$stickyCol
							.css({ opacity: 0 })
							.add($stickyInsct).css({ left: 0 });
						}
					},
					calcAllowance = function () {
						var a = 0;
						// Calculate allowance
						$t.find('tbody tr:lt(3)').each(function () {
							a += $(this).height();
						});
						
						// Set fail safe limit (last three row might be too tall)
						// Set arbitrary limit at 0.25 of viewport height, or you can use an arbitrary pixel value
						if(a > $w.height()*0.25) {
							a = $w.height()*0.25;
						}
						
						// Add the height of sticky header
						a += $stickyHead.height();
						return a;
					};

				setWidths();
				
				$t.parent('.sticky-wrap').parent().scroll($.throttle(250, function() {
					repositionStickyHead();
					repositionStickyCol();
				}));

				$w
				.load(setWidths)
				.resize($.debounce(250, function () {
					setWidths();
					repositionStickyHead();
					repositionStickyCol();
				}))
				.scroll($.throttle(250, repositionStickyHead));
			}
		}
	});
});
