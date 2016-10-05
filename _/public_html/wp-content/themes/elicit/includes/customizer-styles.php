<?php
/**
 * @package elicit
 */
 
 
function elicit_customizer_css() {
	
	?>

	<style type='text/css'>
	
		<?php if(get_theme_mod( 'link_color' )) : ?>
		.Button, .search-form .search-submit, .item h3
		{ 
			background-color:<?php echo get_theme_mod( 'link_color' ); ?>; 
		}
		<?php endif; ?>			
						
		
		<?php if(get_theme_mod( 'link_color' )) : ?>
		.singleright a:hover, .singleleft a:hover, .metadata a:hover, .navbar-inverse .navbar-nav > .active > a, .navbar-inverse .navbar-nav > .active > a:hover, .navbar-inverse .navbar-nav > .active > a:focus,
		h2 a:hover, .navbar-inverse .navbar-nav > li > a:hover
		{ 
			color:<?php echo get_theme_mod( 'link_color' ); ?>; 
		}
		
		<?php endif; ?>				
		
		<?php if(get_theme_mod( 'link_color' )) : ?>
		.dropdown-menu > .active > a, .dropdown-menu > .active > a:hover, .dropdown-menu > .active > a:focus, .scroll-to-top:hover, .tagcloud a:hover
		{ 
			background-color:<?php echo get_theme_mod( 'link_color' ); ?>; 
		}
		
		<?php endif; ?>		
		
		<?php if(get_theme_mod( 'elicit_sidebar_position' )) : ?>
		.col-md-8
		{ 
			float:<?php echo get_theme_mod( 'elicit_sidebar_position' ); ?>; 
			margin: 20px auto;
		}
		.item
		{
			display: <?php echo get_theme_mod( 'elicit_sidebar_position' ); ?>; 
			
		}
		<?php endif; ?>	
		
		<?php if(get_theme_mod( 'head_back_color' )) : ?>
		.header
		{ 
			background-color:<?php echo get_theme_mod( 'head_back_color' ); ?>; 
		}
		<?php endif; ?>					
		
		.name .url, .tagline
		{ 
			color:#<?php echo get_theme_mod( 'header_textcolor' ); ?>; 
		}
					
		
	</style>

<?php
}
add_action ('wp_head', 'elicit_customizer_css');

function elicit_customizer_inline_css() {
?>
	<style type="text/css">	
	.ui-state-active img {
		border: 2px solid #444;
	}
	#customize-control-theme_color .ui-state-active img {
		width: 71px;
		height: 71px;
	}
	#customize-control-sidebar_settings .ui-state-active img {
		width: 71px;
		height: 46px;
	}
	#input_background_pattern {
		height: 220px;
		overflow: auto;
	}
	#input_background_pattern img {
		width: 70px;
		height: 70px;
	}	
	#input_background_pattern .ui-state-active img {
		width: 66px;
		height: 66px;
	}	
	/* Switch Styles */	
	input[type="checkbox"].ios-switch {
		display: none !important;
	}
	input[type="checkbox"].ios-switch + div {
		vertical-align: middle;
		width: 40px;	height: 20px;
		border: 1px solid rgba(0,0,0,.4);
		background-color: rgba(0, 0, 0, 0.1);
		-webkit-transition-duration: .4s;
		-webkit-transition-property: background-color, box-shadow;
		box-shadow: inset 0 0 0 0px rgba(0,0,0,0.4);
		margin: 15px 1.2em 15px 2.5em;
	}
	input[type="checkbox"].ios-switch:checked + div {
		width: 40px;
		background-position: 0 0;
		background-color: #3b89ec;
		border: 1px solid #0e62cd;
		box-shadow: inset 0 0 0 10px rgba(59,137,259,1);
	}
	input[type="checkbox"].tinyswitch.ios-switch + div {
		width: 34px;	height: 18px;
	}
	input[type="checkbox"].bigswitch.ios-switch + div {
		width: 70px;	height: 25px;
	}
	input[type="checkbox"].green.ios-switch:checked + div {
		background-color: #00e359;
		border: 1px solid rgba(0, 162, 63,1);
		box-shadow: inset 0 0 0 10px rgba(0,227,89,1);
	}
	input[type="checkbox"].ios-switch + div > div {
		float: left;
		width: 18px; height: 18px;
		border-radius: inherit;
		background: #ffffff;
		-webkit-transition-timing-function: cubic-bezier(.54,1.85,.5,1);
		-webkit-transition-duration: 0.4s;
		-webkit-transition-property: transform, background-color, box-shadow;
		-moz-transition-timing-function: cubic-bezier(.54,1.85,.5,1);
		-moz-transition-duration: 0.4s;
		-moz-transition-property: transform, background-color;
		box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.3), 0px 0px 0 1px rgba(0, 0, 0, 0.4);
		pointer-events: none;
		margin-top: 1px;
		margin-left: 1px;
	}
	input[type="checkbox"].ios-switch:checked + div > div {
		-webkit-transform: translate3d(20px, 0, 0);
		-moz-transform: translate3d(20px, 0, 0);
		background-color: #ffffff;
		box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.3), 0px 0px 0 1px rgba(8, 80, 172,1);
	}
	input[type="checkbox"].tinyswitch.ios-switch + div > div {
		width: 16px; height: 16px;
		margin-top: 1px;
	}
	input[type="checkbox"].tinyswitch.ios-switch:checked + div > div {
		-webkit-transform: translate3d(16px, 0, 0);
		-moz-transform: translate3d(16px, 0, 0);
		box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.3), 0px 0px 0 1px rgba(8, 80, 172,1);
	}
	input[type="checkbox"].bigswitch.ios-switch + div > div {
		width: 23px; height: 23px;
		margin-top: 1px;
	}
	input[type="checkbox"].bigswitch.ios-switch:checked + div > div {
		-webkit-transform: translate3d(25px, 0, 0);
		-moz-transform: translate3d(16px, 0, 0);
		box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.3), 0px 0px 0 1px rgba(8, 80, 172,1);
	}
	input[type="checkbox"].green.ios-switch:checked + div > div {
		box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.3), 0 0 0 1px rgba(0, 162, 63,1);
		float: right;
		margin-right: 16px;
	}
	.ios-switch-div {
		margin: 1px !important;
		margin-bottom: 10px !important;
	}
	.prefix-upsell-link {
		display: inline-block;
		background-color: #C60000;
		color : #fff;
		text-transform: uppercase;
		margin-top: 6px;
		padding: 3px 6px;
		font-size: 9px;
		letter-spacing: 1px;
		line-height: 1.5;
		clear: both;
	}
	.prefix-upsell-link:hover {
		background-color: #444;
		color : #fff;
	}
	</style>
	<?php
}
add_action( 'admin_enqueue_scripts', 'elicit_customizer_inline_css' );