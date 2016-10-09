<?php



class Elicit_Social_Profile extends WP_Widget {



	protected $defaults;

	protected $sizes;

	protected $profiles;



	function __construct() {



		$this->defaults = array(

			'title'			=> '',

			'new_tab'		=> 0,

			'size'			=> 32,

			'facebook'		=> '',

			'twitter'		=> '',			

			'gplus'			=> '',	

			'linkedin'		=> '',

		);



		$this->sizes = array( '32' );



		$this->profiles = array(

			'facebook' => array(

				'label'	  => __( 'Facebook URI', 'elicit' ),

				'pattern' => '<li class="social-facebook"><a title="Facebook" href="%s" %s><i class="fa fa-facebook"></i></a></li>',

			),

			'twitter' => array(

				'label'	  => __( 'Twitter URI', 'elicit' ),

				'pattern' => '<li class="social-twitter"><a title="Twitter" href="%s" %s><i class="fa fa-twitter"></i></a></li>',

			),

			'gplus' => array(

				'label'	  => __( 'Google+ URI', 'elicit' ),

				'pattern' => '<li class="social-gplus"><a title="Google+" href="%s" %s><i class="fa fa-google-plus"></i></a></li>',

			),

				

			'linkedin' => array(

				'label'	  => __( 'Linkedin URI', 'elicit' ),

				'pattern' => '<li class="social-linkedin"><a title="LinkedIn" href="%s" %s><i class="fa fa-linkedin"></i></a></li>',

			),			

		);



		$widget_ops = array(

			'classname'	 => 'Elicit_Social_Profile',

			'description' => esc_html__( 'Show social profile icons.', 'elicit' ),

		);

		$control_ops = array(

			'id_base' => 'social-icons',

			#'width'   => 505,

			#'height'  => 350,

		);



		parent::__construct ( 'social-icons', esc_html__( 'Elicit Social Icons', 'elicit' ), $widget_ops, $control_ops );



	}



	function form( $instance ) {



		/** Merge with defaults */

		$instance = wp_parse_args( (array) $instance, $this->defaults );

		?>



		<p><label for="<?php echo $this->get_field_id( 'title' ); ?>"><?php _e( 'Title:', 'elicit' ); ?></label> <input class="widefat" id="<?php echo $this->get_field_id( 'title' ); ?>" name="<?php echo $this->get_field_name( 'title' ); ?>" type="text" value="<?php echo esc_attr( $instance['title'] ); ?>" /></p>



		<p><label><input id="<?php echo $this->get_field_id( 'new_tab' ); ?>" type="checkbox" name="<?php echo $this->get_field_name( 'new_tab' ); ?>" value="1" <?php checked( 1, $instance['new_tab'] ); ?>/> <?php esc_html_e( 'Open links in a new tab?', 'elicit' ); ?></label></p>



		<hr style="background: #ccc; border: 0; height: 1px; margin: 20px 0;" />



		<?php

		foreach ( (array) $this->profiles as $profile => $data ) {



			printf( '<p><label for="%s">%s:</label>', esc_attr( $this->get_field_id( $profile ) ), esc_attr( $data['label'] ) );

			printf( '<input type="text" id="%s" class="widefat" name="%s" value="%s" /></p>', esc_attr( $this->get_field_id( $profile ) ), esc_attr( $this->get_field_name( $profile ) ), esc_url( $instance[$profile] ) );



		}



	}



	function update( $newinstance, $oldinstance ) {



		foreach ( $newinstance as $key => $value ) {



			/** Sanitize Profile URIs */

			if ( array_key_exists( $key, (array) $this->profiles ) ) {

				$newinstance[$key] = esc_url( $newinstance[$key] );

			}



		}



		return $newinstance;

	}



	function widget( $args, $instance ) {



		extract( $args );



		/** Merge with defaults */

		$instance = wp_parse_args( (array) $instance, $this->defaults );

		echo $before_widget;



			if ( ! empty( $instance['title'] ) )

				echo $before_title . apply_filters( 'widget_title', $instance['title'], $instance, $this->id_base ) . $after_title;



			$output = '';



			$new_tab = $instance['new_tab'] ? 'target="_blank"' : '';



			foreach ( (array) $this->profiles as $profile => $data ) {

				if ( ! empty( $instance[$profile] ) )

					$output .= sprintf( $data['pattern'], esc_url( $instance[$profile] ), $new_tab );

			}



			

				printf( '<ul  class="fb-like "  style="padding-right:0px" data-href="https://www.facebook.com/wacteamvn/" data-layout="button_count" data-action="like" data-size="large" data-show-faces="true" data-share="true"></ul>
				<ul class="fb-save" data-uri="http://wacteam.com/" data-size="large"></ul></div>', '',$output );



		echo $after_widget;

	}

	

}