
<div class="nxthemesToolbox" id="nxthemesStylePicker">

<div class="title">
<img class="close" onclick="javascript:NXThemesStyleEditor.closeStylePicker()"
     src="${skinPath}/img/close-button.png" width="14" height="14" alt="" />
  Presets - ${style_category}</div>

  <div class="header">PRESETS:
    <div>
      <select id="stylePickerGroupName" onchange="NXThemesStyleEditor.setPresetGroup(this)">
        <#if !selected_preset_group>
          <option value="" selected="selected">Custom presets</option>
        <#else>
          <option value="">Custom presets</option>
        </#if>
        <#list preset_groups as preset_group>
          <#if preset_group == selected_preset_group>
            <option value="${preset_group}" selected="selected">${preset_group}</option>
          <#else>
            <option value="${preset_group}">${preset_group}</option>
          </#if>
        </#list>
      </select>
    </div>
  </div>

  <div class="frame">
    <#if !selected_preset_group>
      <a class="addPreset" href="javascript:void(0)" onclick="NXThemesEditor.addPreset('&quot;${current_theme_name}&quot;', '&quot;${style_category}&quot;')">ADD PRESET</a>
    </#if>
    <#list presets_for_selected_group as preset_info>
        <div class="selection" onclick="NXThemesStyleEditor.updateFormField('&quot;${preset_info.name}&quot;')">
           ${preset_info.preview}
        </div>
    </#list>
  </div>

</div>
