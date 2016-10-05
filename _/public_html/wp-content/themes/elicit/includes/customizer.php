<?php
/**
 * elicit Theme Customizer
 *
 * @package elicit
 */

/**
 * Add postMessage support for site title and description for the Theme Customizer.
 *
 * @param WP_Customize_Manager $wp_customize Theme Customizer object.
 */
function elicit_customize_register( $wp_customize ) {
	$wp_customize->get_setting( 'blogname' )->transport         = 'postMessage';
	$wp_customize->get_setting( 'blogdescription' )->transport  = 'postMessage';
	$wp_customize->get_setting( 'header_textcolor' )->transport = 'postMessage';
}
/**
 * Registers all theme related options to the Customizer.
 */
add_action( 'customize_register', 'elicit_customize_register' );
 
function elicit_customizer( $wp_customize ) {
		   class elicit_image_radio_control extends WP_Customize_Control {

 		public function render_content() {

			if ( empty( $this->choices ) )
				return;

			$name = '_customize-radio-' . $this->id;

			?>
			<style>
				#elicit-img-container .elicit-radio-img-img {
					border: 3px solid #DEDEDE;
					margin: 0 5px 5px 0;
					cursor: pointer;
					border-radius: 3px;
					-moz-border-radius: 3px;
					-webkit-border-radius: 3px;
				}
				#elicit-img-container .elicit-radio-img-selected {
					border: 3px solid #AAA;
					border-radius: 3px;
					-moz-border-radius: 3px;
					-webkit-border-radius: 3px;
				}
				input[type=checkbox]:before {
					content: '';
					margin: -3px 0 0 -4px;
				}
			</style>
			<span class="customize-control-title"><?php echo esc_html( $this->label ); ?></span>
			<ul class="controls" id = 'elicit-img-container'>
			<?php
				foreach ( $this->choices as $value => $label ) :
					$class = ($this->value() == $value)?'elicit-radio-img-selected elicit-radio-img-img':'elicit-radio-img-img';
					?>
					<li style="display: inline;">
					<label>
						<input <?php $this->link(); ?>style = 'display:none' type="radio" value="<?php echo esc_attr( $value ); ?>" name="<?php echo esc_attr( $name ); ?>" <?php $this->link(); checked( $this->value(), $value ); ?> />
						<img src = '<?php echo esc_html( $label ); ?>' class = '<?php echo $class; ?>' />
					</label>
					</li>
					<?php
				endforeach;
			?>
			</ul>
			<script type="text/javascript">

				jQuery(document).ready(function($) {
					$('.controls#elicit-img-container li img').click(function(){
						$('.controls#elicit-img-container li').each(function(){
							$(this).find('img').removeClass ('elicit-radio-img-selected') ;
						});
						$(this).addClass ('elicit-radio-img-selected') ;
					});
				});

			</script>
			<?php
		}
	}
 		//Add "Switcher" support to the theme customizer
		class elicit_Customizer_Switcher_Control extends WP_Customize_Control {
			public $type = 'switcher';
		 
			public function render_content() {
				?>
					<label>
						<span class="customize-control-title"><?php echo esc_html( $this->label ); ?></span>
						<input class="ios-switch green bigswitch" type="checkbox" value="<?php echo esc_attr( $this->value() ); ?>" <?php $this->link(); checked( $this->value() ); ?> /><div class="ios-switch-div" ><div></div></div>
					</label>				
				<?php
			}
		}

	require_once(get_template_directory() . '/includes/customizer-controls.php');
	
	// Main option Settings Panel
	$wp_customize->add_panel ('elicit_panel', array(
	'title' => __('Elicit Theme Options', 'elicit'),
	'priority' => '1'));

	// add new section
	$wp_customize->add_section( 'elicit_theme_colors', array(
	'panel'    => 'elicit_panel',
	'title' => __( 'Theme Colors', 'elicit' ),
	'priority' => 1,
	) );


	//primary colors option
	$wp_customize->add_setting( 'link_color', array(
	'sanitize_callback' => 'elicit_sanitize_hexcolor',
	) );

	$wp_customize->add_control( new WP_Customize_Color_Control( $wp_customize, 'link_color', array(
	'label' => __( 'primary colors', 'elicit' ),
	'section' => 'elicit_theme_colors',
	'settings' => 'link_color',
	) ) );
	
	//Custom css
	class elicit_custom_css_Control extends WP_Customize_Control {

	public $type = 'custom_css';

	public function render_content() {
	  
	?>
	 <label>
		<span class="customize-control-title"><?php echo esc_html( $this->label ); ?></span>
		<textarea rows="5" style="width:100%;" <?php $this->link(); ?>><?php echo esc_textarea( $this->value() ); ?></textarea>
	 </label>
	<?php
	}

	}

	$wp_customize->add_section('elicit_custom_css_setting', array(
	'priority' => 9,
	'title' => __('Custom CSS', 'elicit'),
	'panel' => 'elicit_panel'
	));

	$wp_customize->add_setting('elicit_custom_css', array(
	'default' => '',
	'capability' => 'edit_theme_options',
	'sanitize_callback' => 'wp_filter_nohtml_kses',
	'sanitize_js_callback' => 'wp_filter_nohtml_kses'
	));
	
	$wp_customize->add_control(new elicit_custom_css_Control($wp_customize, 'elicit_custom_css', array(
	'label' => __('Custom CSS', 'elicit'),
	'section' => 'elicit_custom_css_setting',
	'settings' => 'elicit_custom_css'
	)));

	//Sidebar Layout Style
	$wp_customize->add_section( 'elicit_sidebar_position', array(
		'panel'    => 'elicit_panel',
		'title' => __( 'Layout Design', 'elicit' ),
		'priority' => 1,
	) );
	
		$wp_customize->add_setting('elicit_sidebar_position', array(
		'default'           => 'left',
		'sanitize_callback' => 'elicit_sanitize_layout',
			
		));
		$wp_customize->add_control(
			new WP_Customize_Control(
				$wp_customize,
				'elicit_sidebar_position',
				array(
					'label'          => __( 'Select sidebar position', 'elicit' ),
					'section'        => 'elicit_sidebar_position',
					'settings'       => 'elicit_sidebar_position',
					'type'           => 'select',
					'choices'        => array(
						'right'   => __( 'Left','elicit' ),
						'left'  => __( 'Right','elicit' ),
						'none'=> __( 'No Sidebar','elicit' ),

					)
				)
			)
		);	
 	
	
	$wp_customize->add_setting( 'elicit_lite_color_message',
		array(
			'sanitize_callback' => 'elicit_lite_sanitize_text'
		)
	);
	$wp_customize->add_control( new elicit_Lite_Misc_Control( $wp_customize, 'elicit_lite_color_message',
		array(
			'section'     => 'colors',
			'type'        => 'custom_message',
		)
	));
	$wp_customize->add_section( 'elicit_content_setting', array(
		'panel'    => 'elicit_panel',
		'title' => __( 'Content Options', 'elicit' ),
		'priority' => 2,
	) );

	//Show or hide Post excerp
	$wp_customize->add_setting( 
		'bl_post_excerpt' , array(
			'default'     => 1,
			'sanitize_callback' => 'elicit_sanitize_checkbox',
			)
	);

	$wp_customize->add_control(
		new elicit_Customizer_Switcher_Control(
			$wp_customize,	
			'bl_post_excerpt', array(
				'label' =>  __('Post Excerpt','elicit'),
				'section' => 'elicit_content_setting',
			)
		)
	);	
//Post excerp length
	$wp_customize->add_setting( 'excerpt_length', array(
		'default'           => 55,
		'sanitize_callback' => 'absint',
	) );
	$wp_customize->add_control( 'excerpt_length', array(

		'type'        => 'number',
		
		'input_attrs' => array(
			'min'  => 10,
			'step' => 1,
		),
		'section' => 'elicit_content_setting',
		'label'       => esc_html__( 'Excerpt Length', 'elicit' ),
	) );  	

	//Footer copyright text
	$wp_customize->add_section( 'footer_Copyright', array(
	'panel'    => 'elicit_panel',
	'title' => __( 'Footer Copyright', 'elicit' ),
	'priority' => 70,
	) );

	$wp_customize->add_setting( 'Copyright_text', array(
	'sanitize_callback' => 'elicit_sanitize_text'
	) );

	$wp_customize->add_control('Copyright_text', array(
	'type' => 'textarea',
	'label' => __('Footer Copyright', 'elicit'),
	'section' => 'footer_Copyright',
	'settings' => 'Copyright_text',
	) );
	$wp_customize->add_section( 'elicit_logo_section' , array(
		'title'       => __( 'Logo', 'elicit' ),
		'priority'    => 5,
	) );
	$wp_customize->add_setting( 'logo', array(
		'sanitize_callback' => 'esc_url_raw',
	) );
	$wp_customize->add_control( new WP_Customize_Image_Control( $wp_customize, 'logo', array(
		'section'     => 'elicit_logo_section',
		'label'       => esc_html__( 'Logo', 'elicit' ),
	) ) );
	
	//Header background color
	$wp_customize->add_setting( 'head_back_color', array(
	'sanitize_callback' => 'elicit_sanitize_hexcolor',
	) );

	$wp_customize->add_control( new WP_Customize_Color_Control( $wp_customize, 'head_back_color', array(
	'label' => __( 'Header Background', 'elicit' ),
	'section' => 'colors',
	'settings' => 'head_back_color',
	) ) );	
	
	
}
add_action( 'customize_register', 'elicit_customizer' );

/**
* Sanitzie checkbox for WordPress customizer
*/
function elicit_sanitize_checkbox( $input ) {
if ( $input == 1 ) {
	return 1;
} else {
	return '';
}
}
/* Sanitize number */

function bl_sanitize_number( $int ) {
	return absint( $int );
}
function elicit_lite_sanitize_text( $string ) {
	return wp_kses_post( force_balance_tags( $string ) );
}
/**
*Sanitization callback function: colors
*/
function elicit_sanitize_hexcolor($color) {
if ($unhashed = sanitize_hex_color_no_hash($color))
	return '#' . $unhashed;
return $color;
}
// Sanitize text
function elicit_sanitize_text( $input ) {
	return strip_tags( $input);
}
/**
 * Adds sanitization callback function: Sidebar Layout
 */
 
function elicit_sanitize_layout( $input ) {
    $valid = array(
		'right'   => __( 'Left','elicit' ),
		'left'  => __( 'Right','elicit' ),
		'none'=> __( 'No Sidebar','elicit' ),
    );
 
    if ( array_key_exists( $input, $valid ) ) {
        return $input;
    } else {
        return '';
    }
}
 
function elicit_lite_customize_js() {
	wp_enqueue_script( 'elicit_lite_customizer', get_template_directory_uri() . '/js/customizer.js', array( 'customize-controls' ), '20130508', true );
}
add_action( 'customize_controls_print_scripts', 'elicit_lite_customize_js' );