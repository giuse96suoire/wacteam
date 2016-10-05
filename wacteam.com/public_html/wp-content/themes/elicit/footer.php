<div class="Footer">
<div class="container footer-inner" >
<div class="row">
<div class="Footer-widget">
<?php if ( ! is_active_sidebar( 'footer-1' ) && ! is_active_sidebar( 'footer-2' ) && ! is_active_sidebar( 'footer-3' ) )
	?>
		<?php if ( is_active_sidebar( 'footer-1' ) ) : ?>
		<div class="col-sm-4" role="complementary">
			<?php dynamic_sidebar( 'footer-1' ); ?>
		</div><!-- .widget-area .first -->
		<?php endif; ?>

		<?php if ( is_active_sidebar( 'footer-2' ) ) : ?>
		<div class="col-sm-4" role="complementary">
			<?php dynamic_sidebar( 'footer-2' ); ?>
		</div>
		<?php endif; ?>

		<?php if ( is_active_sidebar( 'footer-3' ) ) : ?>
		<div class="col-sm-4" role="complementary">
			<?php dynamic_sidebar( 'footer-3' ); ?>
		</div><!-- .widget-area .third -->
		<?php endif; ?>
</div>
</div>
</div> 
</div>

<div class="page-footer">
<p><?php echo get_theme_mod('Copyright_text');?>
<?php _e( '&nbspTheme by Mizmizi - Develop by WeAreCoders Team ', 'elicit' );?>
</div>
<div class="scroll-to-top"><i class="fa fa-angle-up"></i></div>		
<?php wp_footer(); ?>	
</body>
</html>