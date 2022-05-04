#version 420

in vec3 pos;
in vec3 view_pos;

out vec4 frag_color;

/* fog */
/* levels */

void main() {
    float axisK = levels(pos, .002, .002 + .001 / gl_FragCoord.w, 5, vec3(1, 1, 1));

    vec4 color = mix(vec4(0, 0, 0, 1), vec4(1), axisK);

    frag_color = fog(color, pos, view_pos);
}