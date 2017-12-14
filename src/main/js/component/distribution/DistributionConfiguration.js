import React, { Component } from 'react';

import styles from '../../../css/distributionConfig.css';

import GroupEmailJobConfiguration from './job/GroupEmailJobConfiguration';
import HipChatJobConfiguration from './job/HipChatJobConfiguration';
import SlackJobConfiguration from './job/SlackJobConfiguration';
import EditTableCellFormatter from './EditTableCellFormatter';

import JobAddModal from './JobAddModal';

import {ReactBsTable, BootstrapTable, TableHeaderColumn, InsertButton, DeleteButton} from 'react-bootstrap-table';
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css';

	var jobs = [];

	function addJobs() {
		jobs.push({
			distributionConfigId: '0',
			name: 'Test Job',
			distributionType: 'Group Email',
			lastRun: '12/01/2017 00:00:00',
			status: 'Success',
            frequency: 'DAILY',
            notificationType: [
            'POLICY_VIOLATION',
            'POLICY_VIOLATION_CLEARED',
            'POLICY_VIOLATION_OVERRIDE'],
            groupName: 'Custom Group',
            configuredProjects: ['PSTestApp']
		});
		jobs.push({
			distributionConfigId: '1',
			name: 'Alert Slack Job',
			distributionType: 'Slack',
			lastRun: '12/02/2017 00:00:00',
			status: 'Failure',
            frequency: 'REAL_TIME',
            notificationType: [
            'POLICY_VIOLATION_OVERRIDE',
            'HIGH_VULNERABILITY'],
            configuredProjects: ['missing-1', 'missing-2']
		});
		jobs.push({
			distributionConfigId: '2',
			name: 'HipChat Job',
			distributionType: 'HipChat',
			lastRun: '1/01/2017 00:00:00',
			status: 'Success',
            frequency: 'DAILY',
            notificationType: [
            'POLICY_VIOLATION',
            'POLICY_VIOLATION_CLEARED',
            'POLICY_VIOLATION_OVERRIDE',
            'HIGH_VULNERABILITY',
            'MEDIUM_VULNERABILITY',
            'LOW_VULNERABILITY'],
            includeAllProjects: true,
            configuredProjects: []
		});
	}


	addJobs();

class DistributionConfiguration extends Component {
	constructor(props) {
		super(props);
		 this.state = {
			configurationMessage: '',
			errors: {},
			jobs: [],
			projects: [],
			groups: [],
			waitingForProjects: true,
			waitingForGroups: true
		};
		this.createCustomModal = this.createCustomModal.bind(this);
		this.createCustomDeleteButton = this.createCustomDeleteButton.bind(this);
		this.createCustomInsertButton = this.createCustomInsertButton.bind(this);
		this.cancelJobSelect = this.cancelJobSelect.bind(this);
        this.editButtonClick = this.editButtonClick.bind(this);
        this.handleSetState = this.handleSetState.bind(this);
        this.customJobConfigDeletionConfirm = this.customJobConfigDeletionConfirm.bind(this);
	}

	componentDidMount() {
		var self = this;

		fetch('/hub/projects',{
			credentials: "same-origin"
		})
		.then(function(response) {
			self.handleSetState('waitingForProjects', false);
			if (!response.ok) {
				return response.json().then(json => {
					self.handleSetState('projectTableMessage', json.message);
				});
			} else {
				return response.json().then(json => {
					self.handleSetState('projectTableMessage', '');
					var jsonArray = JSON.parse(json.message);
					if (jsonArray != null && jsonArray.length > 0) {
						var projects = [];
						for (var index in jsonArray) {
							projects.push({
								name: jsonArray[index].name,
								url: jsonArray[index].url
							});
						}
						self.setState({
							projects
						});
					}
				});
			}
		});

		fetch('/hub/groups',{
			credentials: "same-origin",
		})
		.then(function(response) {
			self.handleSetState('waitingForGroups', false);
			if (!response.ok) {
				return response.json().then(json => {
					self.handleSetState('groupError', json.message);
				});
			} else {
				return response.json().then(json => {
					self.handleSetState('groupError', '');
					var jsonArray = JSON.parse(json.message);
					if (jsonArray != null && jsonArray.length > 0) {
						var groups = [];
						for (var index in jsonArray) {
							groups.push({
								name: jsonArray[index].name,
								active: jsonArray[index].active,
								url: jsonArray[index].url
							});
						}
						self.setState({
							groups
						});
					}
				});
			}
		});
        this.fetchDistributionJobs(jobs);
    }

    fetchDistributionJobs(jobsArray) {
        fetch('/configuration/distribution/common',{
			credentials: "same-origin",
            headers: {
				'Content-Type': 'application/json'
			}
		})
		.then(function(response) {
			if (response.ok) {
                response.json().then(jsonArray => {
					if (jsonArray != null && jsonArray.length > 0) {
                        jsonArray.forEach((item) =>{
                            let jobConfig = {
                                distributionConfigId: item.distributionConfigId,
                    			name: item.name,
                    			distributionType: item.distributionType,
                    			lastRun: '',
                    			status: '',
                                frequency: item.frequency,
                                notificationType: [item.notificationType],
                                configuredProjects: item.configuredProjects
                            };
                            jobs.push(jobConfig);
                        });
                    }
                });
            }
        });
    }

    statusColumnClassNameFormat(fieldValue, row, rowIdx, colIdx) {
		var className = styles.statusSuccess;
		if (fieldValue === 'Failure') {
			className = styles.statusFailure;
		}
		return className;
	}

	typeColumnDataFormat(cell, row) {
		var fontAwesomeClass = "";
		if (cell === 'Group Email') {
			fontAwesomeClass = 'fa fa-envelope';
		} else if (cell === 'HipChat') {
			fontAwesomeClass = 'fa fa-comments';
		} else if (cell === 'Slack') {
			fontAwesomeClass = 'fa fa-slack';
		}
		var cellText = " " + cell;
		var data = <div>
						<i key="icon" className={fontAwesomeClass} aria-hidden='true'></i>
						{cellText}
					</div>;

		return data;
	}

    createCustomModal(onModalClose, onSave, columns, validateState, ignoreEditable) {
        return (
	    	<JobAddModal
	    		waitingForProjects={this.state.waitingForProjects}
	    		waitingForGroups={this.state.waitingForGroups}
	    		projects={this.state.projects}
	    		includeAllProjects={true}
	    		groups={this.state.groups}
	    		groupError={this.state.groupError}
	    		projectTableMessage={this.state.projectTableMessage}
	    		handleCancel={this.cancelJobSelect}
		    	onModalClose= { onModalClose }
		    	onSave= { onSave }
		    	columns={ columns }
		        validateState={ validateState }
		        ignoreEditable={ ignoreEditable } />
	    );
	}

	customJobConfigDeletionConfirm(next, dropRowKeys) {
	  if (confirm("Are you sure you want to delete these Job configurations?")) {
	  	//TODO delete the Job configs from the backend
	  	// dropRowKeys are the Id's of the Job configs
	  	console.log('Deleting the Job configs');
	    next();
	  }
	}

	createCustomDeleteButton(onClick) {
		return (
			<DeleteButton
			className={styles.deleteJobButton}/>
		);
	}

	createCustomInsertButton(onClick) {
		return (
			<InsertButton
			className={styles.addJobButton}
			/>
		);
	}

	handleSetState(name, value) {
		this.setState({
			[name]: value
		});
	}

	cancelJobSelect() {
		this.setState({
			currentJobSelected: null
		});
	}

	getCurrentJobConfig(currentJobSelected){
		let currentJobConfig = null;
		if (currentJobSelected != null) {
            const { name, distributionConfigId, distributionType, frequency, notificationType, groupName, includeAllProjects, configuredProjects } = currentJobSelected;
			if (distributionType === 'Group Email') {
				currentJobConfig = <GroupEmailJobConfiguration buttonsFixed={true} distributionConfigId={distributionConfigId} name={name} includeAllProjects={includeAllProjects} frequency={frequency} notificationType={notificationType} waitingForGroups={this.state.waitingForGroups} groups={this.state.groups} groupName={groupName} waitingForProjects={this.state.waitingForProjects} projects={this.state.projects} configuredProjects={configuredProjects} handleCancel={this.cancelJobSelect} projectTableMessage={this.state.projectTableMessage} />;
			} else if (distributionType === 'HipChat') {
				currentJobConfig = <HipChatJobConfiguration buttonsFixed={true} distributionConfigId={distributionConfigId} name={name} includeAllProjects={includeAllProjects} frequency={frequency} notificationType={notificationType} waitingForProjects={this.state.waitingForProjects} projects={this.state.projects} configuredProjects={configuredProjects} handleCancel={this.cancelJobSelect} projectTableMessage={this.state.projectTableMessage} />;
			} else if (distributionType === 'Slack') {
				currentJobConfig = <SlackJobConfiguration buttonsFixed={true} distributionConfigId={distributionConfigId} name={name} includeAllProjects={includeAllProjects} frequency={frequency} notificationType={notificationType} waitingForProjects={this.state.waitingForProjects} projects={this.state.projects} configuredProjects={configuredProjects} handleCancel={this.cancelJobSelect} projectTableMessage={this.state.projectTableMessage} />;
			}
		}
		return currentJobConfig;
	}

    editButtonClick(cell, row) {
        return <EditTableCellFormatter setParentState={this.handleSetState} currentJobSelected= {row} />;
    }

	render() {
		const jobTableOptions = {
	  		noDataText: 'No jobs configured',
	  		clearSearch: true,
	  		insertBtn: this.createCustomInsertButton,
	  		deleteBtn: this.createCustomDeleteButton,
	  		insertModal: this.createCustomModal,
	  		handleConfirmDeleteRow: this.customJobConfigDeletionConfirm
		};
		const jobsSelectRowProp = {
	  		mode: 'checkbox',
	  		clickToSelect: true,
			bgColor: function(row, isSelect) {
				if (isSelect) {
					return '#e8e8e8';
				}
				return null;
			}
		};
		var content = <div>
						<BootstrapTable data={jobs} containerClass={styles.table} striped hover condensed insertRow={true} deleteRow={true} selectRow={jobsSelectRowProp} search={true} options={jobTableOptions} trClassName={styles.tableRow} headerContainerClass={styles.scrollable} bodyContainerClass={styles.tableScrollableBody} >
	      					<TableHeaderColumn dataField='distributionConfigId' hidden>Job Id</TableHeaderColumn>
	      					<TableHeaderColumn dataField='name' isKey dataSort>Distribution Job</TableHeaderColumn>
	      					<TableHeaderColumn dataField='distributionType' dataSort dataFormat={ this.typeColumnDataFormat }>Type</TableHeaderColumn>
	      					<TableHeaderColumn dataField='lastRun' dataSort>Last Run</TableHeaderColumn>
	      					<TableHeaderColumn dataField='status' dataSort columnClassName={ this.statusColumnClassNameFormat }>Status</TableHeaderColumn>
                            <TableHeaderColumn dataField='' dataFormat={ this.editButtonClick }></TableHeaderColumn>
	  					</BootstrapTable>
	  					<p name="jobConfigTableMessage">{this.state.jobConfigTableMessage}</p>
  					</div>;
		var currentJobContent = this.getCurrentJobConfig (this.state.currentJobSelected);
		if (currentJobContent != null) {
			content = currentJobContent;
		}
		return (
				<div>
					{content}
				</div>
		)
	}
};

export default DistributionConfiguration;
